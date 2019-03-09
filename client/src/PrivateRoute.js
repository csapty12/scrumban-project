import React from "react";
import { Route, Redirect } from "react-router-dom";

const PrivateRoute = ({ component: Component, security, ...otherProps }) => (
  <Route
    {...otherProps}
    render={props =>
      security === true ? (
        <Component {...otherProps} />
      ) : (
        <Redirect to="/login" />
      )
    }
  />
);

export default PrivateRoute;
