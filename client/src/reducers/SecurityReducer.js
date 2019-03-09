import { SET_CURRENT_USER, SET_ACTIVE_PROJECT } from "../actions/Types";

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

const getActiveProject = actionpayload => {
  return actionpayload.activeProject;
};

export default function(state = initialState, action) {
  switch (action.type) {
    case SET_CURRENT_USER:
      return {
        ...state,
        validToken: isTokenValid(action.payload),
        user: action.payload
      };
    case SET_ACTIVE_PROJECT:
      return {
        ...state,
        activeProject: getActiveProject(action.payload)
      };

    default:
      return state;
  }
}
