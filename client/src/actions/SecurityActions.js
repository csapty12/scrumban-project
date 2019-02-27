import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./Types";
import setJwt from "../components/security/setJwt";
import jwt_decode from "jwt-decode";

// export const createNewUser = (newUser, history) => async dispatch => {
//   try {
//     await axios.post("http://localhost:8080/api/users/register", newUser);
//     history.push("/");
//     dispatch({ dispatch: GET_ERRORS, payload: {} });
//   } catch (err) {
//     dispatch({
//       type: GET_ERRORS,
//       payload: JSON.stringify(err)
//     });
//   }
// };

export const login = LoginRequest => async dispatch => {
  try {
    //post => login request
    const res = await axios.post(
      "http://localhost:8080/api/users/login",
      LoginRequest
    );
    //extract the token from response data.
    const { token } = res.data;

    //store token in locaStorage
    localStorage.setItem("jwtToken", token);
    setJwt(token);
    // //set token in headers.
    const decodedToken = jwt_decode(token);
    // //decode the token so we can use it
    // //dispatch to our security reducer.
    dispatch({
      type: SET_CURRENT_USER,
      payload: decodedToken
    });
  } catch (err) {
    console.log("errors: " + JSON.stringify(err));
    dispatch({
      type: GET_ERRORS,
      payload: JSON.stringify(err)
    });
  }
};
