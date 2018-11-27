//this is what will use axios to send the data to the backend.
//upon response, it will dispatch the data to the state
import axios from "axios";
import { GET_ERRORS } from "./Types";

export const createProject = (project, history) => async dispatch => {
  try {
    await axios.post("http://localhost:8080/api/project", project);
    history.push("/dashboard");
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};
