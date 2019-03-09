import React, { Component } from "react";
import "./App.css";
import "./projectItem.css";
import Dashboard from "./components/Project/projectView/Dashboard";
import Navbar from "./components/Layout/Navbar";
import "bootstrap/dist/css/bootstrap.min.css";
import "font-awesome/css/font-awesome.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";
import { HashRouter as Router, Route, Switch } from "react-router-dom";
import ProjectDashboard from "./components/Project/ProjectDashboard/ProjectDashboard";
import Landing from "./components/Layout/Landing";
import Login from "./components/Layout/Login";
import Register from "./components/Layout/Register";

import PrivateRoute from "./PrivateRoute";
import { Provider } from "react-redux";
import store from "./store";
import jwt_decode from "jwt-decode";
import { setJwt } from "./security/SetJwt";
import { SET_CURRENT_USER } from "./actions/Types";

const userJwt = localStorage.getItem("jwt");
if (userJwt) {
  setJwt(userJwt);
  const decodedToken = jwt_decode(userJwt);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decodedToken
  });

  const currentTime = Date.now() / 1000;
  if (decodedToken.exp < currentTime) {
    localStorage.removeItem("jwt");
    setJwt(false);
    store.dispatch({
      type: SET_CURRENT_USER,
      payload: {}
    });
    window.location.href = "/";
  }
}

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <Router>
          <div className="App">
            <Navbar />
            <Route exact path="/" component={Landing} />
            <Route exact path="/login" component={Login} />
            <Route exact path="/register" component={Register} />

            <Switch>
              <PrivateRoute exact path="/dashboard" component={Dashboard} />
              <PrivateRoute
                exact
                path="/dashboard/:projectIdentifier"
                component={ProjectDashboard}
              />
            </Switch>
          </div>
        </Router>
      </Provider>
    );
  }
}

export default App;
