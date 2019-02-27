import axios from "axios";

export default token => {
  if (token) {
    //insert the token into the header of the request.

    axios.defaults.headers.common["Authorization"] = token;
  } else {
    delete axios.defaults.headers.common["Authorization"];
  }
};
