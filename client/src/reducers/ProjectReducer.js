import { GET_PROJECTS, GET_PROJECT } from "../actions/Types"; //you have to import the action type that you want to use for the reducer.

//errors must have an inital state to begin with
const initalState = {
  projects: [], //this will contain all the projects.
  project: {} //when updating a project, this is used to load that single project.
};

export default function(state = initalState, action) {
  switch (action.type) {
    case GET_PROJECTS:
      return {
        ...state,
        projects: action.payload
      };
    case GET_PROJECT:
      return {
        ...state,
        project: action.payload
      };
    default:
      return state;
  }
}
