export const PUBLIC_APIS = ['/auth/login', '/auth/forgotpwd'];

export const TOKEN_KEY = 'nsa_token_key';
export const ACCESS_TOKEN = 'nsa_access_token';

export const DATE_FILTER_FORMAT = 'DD-MM-YYYY';
export const DATE_FILTER_FORMAT_NEW = 'YYYY-MM-DD';
export const DATE_DISPLAY_FORMAT = 'DD/MM/YYYY';
export const FULL_DATE_DISPLAY_FORMAT = 'DD/MM/YYYY HH:mm:ss';
export const BASE_DATE_FORMAT = 'YYYY-MM-DD';
export const FULL_BASE_DATE_FORMAT = 'YYYY-MM-DD HH:mm:ss';

export const PAGINATION = {
  TOTAL: 30
};

export const FLAG_OPTS = {
  0: 'Chưa có yêu cầu',
  1: 'Yêu cầu duyệt',
  2: 'Chuyển thành công',
  3: 'Từ chối duyệt'
};

export const ORDER_AGENCY_STATUS = {
  PENDING: 1,
  CANCEL: 2,
  ACCEPT: 3,
  SHIPPING: 4,
  FINISH: 5
};

export const ORDER_AGENCY_STATUS_TEXT = {
  1: 'Đang chờ duyệt',
  2: 'Đã huỷ',
  3: 'Đã xác nhận',
  4: 'Đang giao hàng',
  5: 'Xong'
};

export const BLACKLIST_STATUS = {
  REQUEST: 0,
  CONFIRMED: 1,
  REJECT: 2
};

export const SHIPPING_METHOD_OPTS = {
  1: 'Giao hàng công ty',
  2: 'Giao hàng đại lý'
};

export const ACCOUNT_STATUS = {
  CANCEL: -1,
  PENDING: 0,
  ACTIVE: 1
};

export const ACCOUNT_STATUS_TEXT = {
  '-1': 'Từ chối xác nhận',
  '0': 'Chờ xác nhận',
  '1': 'Đã xác nhận'
}
