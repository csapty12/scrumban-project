import React from "react";
import { Route, Redirect } from "react-router-dom";
import store from "./store";

const PrivateRoute = ({ component: Component, ...otherProps }) => (
  <Route
    {...otherProps}
    render={props =>
      store.getState().security.validToken === true ? (
        <Component {...props} />
      ) : (
        <Redirect to="/login" />
      )
    }
  />
);

export default PrivateRoute;
