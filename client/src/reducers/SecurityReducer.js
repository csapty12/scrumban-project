import { SET_CURRENT_USER } from "../actions/Types";

const initialState = {
  user: {},
  validToken: false
};

const isTokenValid = payload => {
  if (payload) {
    return true;
  }
  return false;
};

export default function(state = initialState, action) {
  switch (action.type) {
    case SET_CURRENT_USER:
      return {
        ...state,
        validToken: isTokenValid(action.payload),
        user: action.payload
      };

    default:
      return state;
  }
}
