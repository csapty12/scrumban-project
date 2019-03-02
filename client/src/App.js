import React, { Component } from "react";
import "./App.css";
import "./projectItem.css";
import Dashboard from "./components/Project/projectView/Dashboard";
import Navbar from "./components/Layout/Navbar";
import "bootstrap/dist/css/bootstrap.min.css";
import "font-awesome/css/font-awesome.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import ProjectDashboard from "./components/Project/ProjectDashboard/ProjectDashboard";
import Landing from "./components/Layout/Landing";
import { Provider } from "react-redux";
import store from "./store";
import jwt_decode from "jwt-decode";
import setJwt from "./components/security/setJwt";
import { SET_CURRENT_USER } from "./actions/Types";
import SecureRoute from "./components/security/SecureRoute";

const userJwt = localStorage.jwtToken;
let decodedToken;
if (userJwt) {
  setJwt(userJwt);
  decodedToken = jwt_decode(userJwt);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decodedToken
  });
  const currentTime = Date.now() / 1000;
  if (decodedToken.exp < currentTime) {
    localStorage.removeItem("jwtToken");
    setJwt(null);
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
            <Switch>
              <SecureRoute exact path="/dashboard" component={Dashboard} />
              <SecureRoute
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
