//this is what will be used whenever there are any errors that come back from the server.
import { GET_ERRORS } from "../actions/Types";

//errors must have an inital state to begin with
const initalState = {};

export default function(state = initalState, action) {
  switch (action.type) {
    case GET_ERRORS:
      return action.payload;
    default:
      return state;
  }
}
