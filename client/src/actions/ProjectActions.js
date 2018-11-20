//this is what will use axios to send the data to the backend.
import axios from "axios";
import { GET_ERRORS } from "./Types";

export const createProject = (project, history) => async dispatch => {
  try {
    const res = await axios.post("http://localhost:8080/api/project", project);
    history.push("/dashboard");
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};
