<template>
  <div class="agency-table-groups">
    <h2>Quản lý rạp phim</h2>
    <div class="text-right">
      <a-button type="primary" class="editable-add-btn mb-3" @click="showModalAdd">Thêm rạp mới</a-button>
    </div>
    <a-table
      size="middle"
      :columns="columns"
      :dataSource="cinemas"
      :rowKey="record => record.id"
      :loading="tableLoading"
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

      <template slot="dateFormated" slot-scope="text, record">
        {{text && moment(text).format('DD/MM/YYYY') || ""}}
      </template>

      <template slot="userById" slot-scope="text, record">
        {{text && userOpts && userOpts.filter(user => user.id == text) && userOpts.filter(user => user.id == text)[0] && userOpts.filter(user => user.id == text)[0].fullName || ""}}
      </template>

      <template slot="operation" slot-scope="text, record">
        <div class="editable-row-operations">
          <a-button size="small" type="primary" ghost @click="() => handleEdit(record)">Sửa</a-button>
          <a-popconfirm title="Bạn có chắc muốn xóa dữ liệu này?" @confirm="handleDelete(record.id)">
            <a-button size="small" type="danger" ghost>Xóa</a-button>
          </a-popconfirm>
        </div>
      </template>
    </a-table>

    <a-modal
      :maskClosable="false"
      title="Thêm rạp mới"
      :visible="modalAddShow"
      :width="600"
      :footer="null"
      @ok="hideModalAdd"
      @cancel="hideModalAdd"
    >
      <CinemasFormAdd v-if="modalAddShow" @submitted="hideModalAdd" />
    </a-modal>

    <a-modal
      :maskClosable="false"
      v-if="modalEditShow"
      title="Sửa thông tin rạp"
      :visible="modalEditShow"
      :width="600"
      :footer="null"
      @ok="hideModalEdit"
      @cancel="hideModalEdit"
    >
      <CinemasFormEdit v-if="modalEditShow" @submitted="hideModalEdit" :initialValues="editData" />
    </a-modal>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex';
import moment from 'moment';
import CinemasFormAdd from '~/components/Cinemas/CinemasFormAdd';
import CinemasFormEdit from '~/components/Cinemas/CinemasFormEdit';

export default {
  head() {
    return {
      title: 'Quản lý rạp phim'
    };
  },

  components: {
    CinemasFormAdd,
    CinemasFormEdit
  },

  async fetch({ store }) {
    await store.dispatch('cinemas/get');
    await store.dispatch('users/getOptions');
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
      columns: [
        {
          title: 'STT',
          scopedSlots: {
            customRender: 'stt'
          },
          width: 60
        },
        {
          title: 'Tên rạp',
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
          title: 'Số điện thoại',
          dataIndex: 'phone'
        },
        {
          title: 'Địa chỉ',
          dataIndex: 'address'
        },
        {
          title: 'Thành phố',
          dataIndex: 'city'
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
    ...mapState('users', ['userOpts']),
    ...mapState('cinemas', ['cinemas'])
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

    async handleDelete(id) {
      this.buttonLoading = true;
      try {
        const doDelete = await this.$store.dispatch('cinemas/delete', id);
        if (!doDelete.success && doDelete.err_msg) {
          this.$message.error(doDelete.err_msg);
        } else {
          this.$message.success('Xóa rạp thành công!');
          await this.reloadTableData();
        }
      } catch (error) {
          this.$message.error('Xóa rạp không thành công');
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

    async reloadTableData(reloadOption) {
      this.tableLoading = true;
      await this.$store.dispatch('cinemas/get', {});
      this.tableLoading = false;
    },

    handleTableChange(pagination) {
      this.pagination = pagination;
    }
  }
};
</script>