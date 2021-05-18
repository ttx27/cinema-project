<template>
  <div class="agency-table-groups">
    <h2>Quản lý phim</h2>
    <div class="text-right">
      <a-popconfirm :disabled="buttonLoading || !selectedRowKeys || selectedRowKeys.length == 0" title="Bạn có chắc muốn xóa những dữ liệu này?" @confirm="handleDeleteSelected">
        <a-button :loading="buttonLoading" type="danger" :disabled="!selectedRowKeys || selectedRowKeys.length == 0">
          Xóa phim đã chọn
        </a-button>
      </a-popconfirm>
      <a-button type="primary" class="editable-add-btn mb-3 ml-3" @click="showModalAdd">Thêm phim mới</a-button>
    </div>
    <a-table
      size="middle"
      :columns="columns"
      :dataSource="movies"
      :rowKey="record => record.id"
      :loading="tableLoading"
      :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
      @change="handleTableChange"
    >
      <div
        slot="filterDropdown"
        slot-scope="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }"
        style="padding: 8px"
      >
        <a-input
          v-ant-ref="c => searchInput = c"
          :placeholder="`Search ${column.dataIndex}`"
          :value="selectedKeys[0]"
          @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
          @pressEnter="() => handleSearch(selectedKeys, confirm)"
          style="width: 188px; margin-bottom: 8px; display: block;"
        />
        <a-button
          type="primary"
          @click="() => handleSearch(selectedKeys, confirm)"
          icon="search"
          size="small"
          style="width: 90px; margin-right: 8px"
        >Search</a-button>
        <a-button @click="() => handleReset(clearFilters)" size="small" style="width: 90px">Reset</a-button>
      </div>

      <a-icon
        slot="filterIcon"
        slot-scope="filtered"
        type="search"
        :style="{ color: filtered ? '#108ee9' : undefined }"
      />

      <template
        slot="stt"
        slot-scope="text, record, index"
      >{{ ((pagination.current || 1) - 1)*10 + index + 1 }}</template>

      <template
        slot="status"
        slot-scope="text, record, index"
      >{{text == 0 ? 'Đã ngừng chiếu' : 'Đang chiếu'}}</template>

      <template slot="dateFormated" slot-scope="text, record">
        {{text && moment(text).format('DD/MM/YYYY') || ""}}
      </template>

      <template slot="userById" slot-scope="text, record">
        {{text && userOpts && userOpts.filter(user => user.id == text) && userOpts.filter(user => user.id == text)[0] && userOpts.filter(user => user.id == text)[0].fullName || ""}}
      </template>

      <template slot="operation" slot-scope="text, record">
        <div class="editable-row-operations">
          <a-button v-if="record.status == 0" size="small" type="primary" @click="() => handleEdit(record)" ghost>Bắt đầu chiếu</a-button>
          <a-popconfirm v-else title="Bạn có chắc muốn dừng chiếu phim này?" @confirm="handleChangeStatus(record, 0)">
            <a-button size="small" type="danger" ghost>Dừng chiếu</a-button>
          </a-popconfirm>
        </div>
      </template>
    </a-table>

    <a-modal
      :maskClosable="false"
      title="Thêm phim mới"
      :visible="modalAddShow"
      :width="600"
      :footer="null"
      @ok="hideModalAdd"
      @cancel="hideModalAdd"
    >
      <MoviesFormAdd v-if="modalAddShow" @submitted="hideModalAdd" />
    </a-modal>

    <a-modal
      :maskClosable="false"
      v-if="modalEditShow"
      title="Sửa thông tin phim"
      :visible="modalEditShow"
      :width="600"
      :footer="null"
      @ok="hideModalEdit"
      @cancel="hideModalEdit"
    >
      <MoviesFormEdit v-if="modalEditShow" @submitted="hideModalEdit" :initialValues="editData" />
    </a-modal>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex';
import moment from 'moment';
import MoviesFormAdd from '~/components/Movies/MoviesFormAdd';
import MoviesFormEdit from '~/components/Movies/MoviesFormEdit';

export default {
  head() {
    return {
      title: 'Quản lý phim'
    };
  },

  components: {
    MoviesFormAdd,
    MoviesFormEdit
  },

  async fetch({ store }) {
    await Promise.all([
      store.dispatch('movies/get'),
      store.dispatch('actors/getOptions'),
      store.dispatch('categories/getOptions'),
      store.dispatch('directors/getOptions'),
      store.dispatch('languages/getOptions'),
      store.dispatch('subtitles/getOptions'),
      store.dispatch('users/getOptions'),
    ]);
  },

  data() {
    return {
      pagination: {},
      searchText: '',
      searchInput: null,
      modalAddShow: false,
      modalEditShow: false,
      tableLoading: false,
      buttonLoading: false,
      editData: {},
      selectedRowKeys: [],
      columns: [
        {
          title: 'STT',
          scopedSlots: {
            customRender: 'stt'
          },
          width: 60
        },
        {
          title: 'Tên phim',
          dataIndex: 'name',
          scopedSlots: {
            filterDropdown: 'filterDropdown',
            filterIcon: 'filterIcon',
          },
          onFilter: (value, record) =>
            record.name
              .toString()
              .toLowerCase()
              .includes(value.toLowerCase()),
          onFilterDropdownVisibleChange: visible => {
            if (visible) {
              setTimeout(() => {
                this.searchInput.focus();
              }, 0);
            }
          },
        },
        {
          title: 'Ngày khởi chiếu',
          dataIndex: 'releaseDate'
        },
        {
          title: 'Độ dài (phút)',
          dataIndex: 'duration'
        },
        {
          title: 'Độ tuổi',
          dataIndex: 'rating'
        },
        {
          title: 'Trạng thái',
          dataIndex: 'status',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: 'Người tạo',
          dataIndex: 'createdBy',
          scopedSlots: { customRender: 'userById' }
        },
        {
          title: 'Ngày tạo',
          dataIndex: 'createdDate',
          scopedSlots: { customRender: 'dateFormated' }
        },
        {
          title: 'Người sửa',
          dataIndex: 'modifiedBy',
          scopedSlots: { customRender: 'userById' }
        },
        {
          title: 'Ngày sửa',
          dataIndex: 'modifiedDate',
          scopedSlots: { customRender: 'dateFormated' }
        },
        {
          title: 'Hành động',
          dataIndex: 'operation',
          width: 80,
          scopedSlots: { customRender: 'operation' }
        }
      ]
    };
  },

  computed: {
    ...mapState('movies', ['movies']),
    ...mapState('users', ['userOpts']),
  },

  methods: {
    moment,
    
    showModalAdd() {
      this.modalAddShow = true;
    },

    async hideModalAdd() {
      this.modalAddShow = false;
      await this.reloadTableData(true);
    },

    showModalEdit() {
      this.modalEditShow = true;
    },

    async hideModalEdit() {
      this.modalEditShow = false;
      this.editData = {};
      await this.reloadTableData(true);
    },

    handleEdit(editData) {
      this.editData = editData;
      this.showModalEdit();
    },

    async handleDeleteSelected() {
      this.buttonLoading = true;
      try {
        const doDelete = await Promise.all(this.selectedRowKeys.map(id => this.$store.dispatch('movies/delete', id)));
        if (!doDelete.success && doDelete.err_msg) {
          this.$message.error(doDelete.err_msg);
        } else {
          this.$message.success('Xóa phim thành công!');
          await this.reloadTableData();
        }
      } catch (error) {
          this.$message.error('Xóa phim không thành công');
      }
      this.buttonLoading = false;
    },

    async handleDelete(id) {
      this.buttonLoading = true;
      try {
        const doDelete = await this.$store.dispatch('movies/delete', id);
        if (!doDelete.success && doDelete.err_msg) {
          this.$message.error(doDelete.err_msg);
        } else {
          this.$message.success('Xóa phim thành công!');
          await this.reloadTableData();
        }
      } catch (error) {
          this.$message.error('Xóa phim không thành công');
      }
      this.buttonLoading = false;
    },

    async handleChangeStatus(movie, status) {
      this.buttonLoading = true;
      try {
        const doEdit = await this.$store.dispatch('movies/edit', {
          ...movie,
          releaseDate: moment(movie.releaseDate).format('DD/MM/YYYY'),
          status,
          categories: movie.categories.map(item => item.id),
          directors: movie.directors.map(item => item.id),
          actors: movie.actors.map(item => item.id),
          languages: movie.languages.map(item => item.id),
          subtitles: movie.subtitles.map(item => item.id),
        });

        if (doEdit.errors || doEdit.message) {
          this.$message.error((doEdit.errors && doEdit.errors.message) || doEdit.message);
        } else {
          this.$message.success("Cập nhật thông tin phim thành công!");
          await this.reloadTableData();
        }
      } catch (error) {
          this.$message.error('Xóa phim không thành công');
      }
      this.buttonLoading = false;
    },

    handleSearch(selectedKeys, confirm) {
      confirm();
      this.searchText = selectedKeys[0];
    },

    handleReset(clearFilters) {
      clearFilters();
      this.searchText = '';
    },

    onSelectChange(selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys;
    },

    async reloadTableData(reloadOption) {
      this.tableLoading = true;
      await this.$store.dispatch('movies/get', {});
      this.selectedRowKeys = [];
      this.tableLoading = false;
    },

    handleTableChange(pagination) {
      this.pagination = pagination;
    }
  }
};
</script>