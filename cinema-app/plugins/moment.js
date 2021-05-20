import Vue from "vue";
import moment from "moment";
import {
  momentToDateDisplay,
  momentToFullDateDisplay,
  momentBaseDate
} from "~/utils/moment";

moment.locale("vi");
Vue.filter("momentToDateDisplay", (date) => momentToDateDisplay(date));
Vue.filter("momentToFullDateDisplay", (date) => momentToFullDateDisplay(date));
Vue.filter("momentBaseDate", (date) => momentBaseDate(date));
