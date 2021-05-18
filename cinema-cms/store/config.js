import * as mutationTypes from "~/utils/mutation-types";

/**
 * initial state
 */
const state = () => ({
  config: {},
  ratings: ["P - Thích hợp cho mọi độ tuổi", "C13 - cấm người dưới 13 tuổi", "C16 - cấm người dưới 16 tuổi", "C18 - cấm người dưới 18 tuổi"],
  cities: [{ "name": "TP HCM", "id": 1 }, { "name": "Hà Nội", "id": 2 }, { "name": "Hải Phòng", "id": 3 }, { "name": "Đà Nẵng", "id": 4 }, { "name": "Cần Thơ", "id": 5 }, { "name": "Phú Yên", "id": 6 }, { "name": "Yên Bái", "id": 7 }, { "name": "Vĩnh Phúc", "id": 8 }, { "name": "Vĩnh Long", "id": 9 }, { "name": "Tuyên Quang", "id": 10 }, { "name": "Trà Vinh", "id": 11 }, { "name": "Tiền Giang", "id": 12 }, { "name": "Thừa Thiên Huế", "id": 13 }, { "name": "Thanh Hóa", "id": 14 }, { "name": "Thái Nguyên", "id": 15 }, { "name": "Thái Bình", "id": 16 }, { "name": "Tây Ninh", "id": 17 }, { "name": "Sơn La", "id": 18 }, { "name": "Sóc Trăng", "id": 19 }, { "name": "Quảng Trị", "id": 20 }, { "name": "Quảng Ninh", "id": 21 }, { "name": "Quảng Ngãi", "id": 22 }, { "name": "Quảng Nam", "id": 23 }, { "name": "Quảng Bình", "id": 24 }, { "name": "Phú Thọ", "id": 25 }, { "name": "Ninh Thuận", "id": 26 }, { "name": "Ninh Bình", "id": 27 }, { "name": "Nghệ An", "id": 28 }, { "name": "Nam Định", "id": 29 }, { "name": "Long An", "id": 30 }, { "name": "Lào Cai", "id": 31 }, { "name": "Lạng Sơn", "id": 32 }, { "name": "Lâm Đồng", "id": 33 }, { "name": "Lai Châu", "id": 34 }, { "name": "Kon Tum", "id": 35 }, { "name": "Kiên Giang", "id": 36 }, { "name": "Khánh Hòa", "id": 37 }, { "name": "Hưng Yên", "id": 38 }, { "name": "Hòa Bình", "id": 39 }, { "name": "Hậu Giang", "id": 40 }, { "name": "Hải Dương", "id": 41 }, { "name": "Hà Tĩnh", "id": 42 }, { "name": "Hà Nam", "id": 43 }, { "name": "Hà Giang", "id": 44 }, { "name": "Gia Lai", "id": 45 }, { "name": "Đồng Tháp", "id": 46 }, { "name": "Đồng Nai", "id": 47 }, { "name": "Điện Biên", "id": 48 }, { "name": "Đắk Nông", "id": 49 }, { "name": "Đắk Lắk", "id": 50 }, { "name": "Cao Bằng", "id": 51 }, { "name": "Cà Mau", "id": 52 }, { "name": "Bình Thuận", "id": 53 }, { "name": "Bình Phước", "id": 54 }, { "name": "Bình Dương", "id": 55 }, { "name": "Bình Định", "id": 56 }, { "name": "Bến Tre", "id": 57 }, { "name": "Bắc Ninh", "id": 58 }, { "name": "Bạc Liêu", "id": 59 }, { "name": "Bắc Kạn", "id": 60 }, { "name": "Bắc Giang", "id": 61 }, { "name": "Bà Rịa - Vũng Tàu", "id": 62 }, { "name": "An Giang", "id": 63 }]
});

/**
 * initial getters
 */
const getters = {
  product: (state) => state.config && state.config.product || {},
  clientType: state => state.config && state.config.client.type || {},
  permissionList: state => state.config && state.config.permission.list || {},
  permissionAssignList: state => state.config && state.config.permission.assignList || {},
  roleList: state => state.config && state.config.role.list || {},
  roleAssignList: state => state.config && state.config.role.assignList || {},
  notificationType: state => state.config && state.config.notification_type || {},
  menu: state => state.config && state.config.menu || {}
};

/**
 * initial actions
 */
const actions = {
  async get({ commit }) {
    const { data = [] } = await this.$repositories.config.index();
    commit(mutationTypes.CONFIG.SET_CONFIG, data);
    return data;
  }
};

/**
 * initial mutations
 */
const mutations = {
  [mutationTypes.CONFIG.SET_CONFIG](state, config) {
    state.config = config;
  }
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
