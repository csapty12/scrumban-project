import React, { Component } from "react";
import "./landing.css";
import store from "../../store";
import { Redirect } from "react-router-dom";

export default class Landing extends Component {
  constructor(props) {
    super(props);
    this.state = {
      redirectToDashboard: false
    };
  }

  setRedirectToDashboard = () => {
    this.setState({ redirectToDashboard: true });
  };
  componentDidMount() {
    if (store.getState().security.validToken) {
      this.setRedirectToDashboard();
    }
  }

  render() {
    return (
      <div className="container text-center">
        {this.state.redirectToDashboard && <Redirect to="/dashboard" />}
        <h1 className="mt-5 text-white font-weight-light">Tellban</h1>
        <p className="lead text-white-50">
          Your Personal Project Management Tool
        </p>
        <hr />
        <button type="button" className="btn btn-primary">
          Login
        </button>
        <button type="button" className="btn btn-success">
          Register
        </button>
      </div>
    );
  }
}
