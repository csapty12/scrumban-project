//this is what will be used whenever there are any errors that come back from the server.
import { GET_ERRORS } from "../actions/Types"; //you have to import the action type that you want to use for the reducer.

//errors must have an inital state to begin with
const initalState = {};

export default function(state = initalState, action) {
  switch (action.type) {
    case GET_ERRORS:
      return action.payload; //this is what will be dispatched to the store.
    default:
      return state;
  }
}
