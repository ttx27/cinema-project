import moment from "moment";
import {
  DATE_DISPLAY_FORMAT,
  FULL_DATE_DISPLAY_FORMAT,
  BASE_DATE_FORMAT,
  FULL_BASE_DATE_FORMAT
} from "~/utils/config";

export function momentToDateDisplay(date) {
  return moment(date, BASE_DATE_FORMAT).format(DATE_DISPLAY_FORMAT);
}

export function momentToFullDateDisplay(date) {
  return moment(date, FULL_BASE_DATE_FORMAT).format(FULL_DATE_DISPLAY_FORMAT);
}

export function momentBaseDate(date) {
  return moment(date).format(BASE_DATE_FORMAT);
}
