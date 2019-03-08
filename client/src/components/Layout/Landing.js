import React, { Component } from "react";
import "./landing.css";

export default class Landing extends Component {
  render() {
    return (
      <div className="container text-center">
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
