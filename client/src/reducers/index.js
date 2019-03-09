import { combineReducers } from "redux";
import SecurityReducer from "./SecurityReducer";

export default combineReducers({
  //this takes in all the reducers that we want to use which will be added to the root reducer, which is put into the store.
  security: SecurityReducer
});
