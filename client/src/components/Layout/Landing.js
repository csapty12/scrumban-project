import React, { Component } from "react";
import "./landing.css";

export default class Landing extends Component {
  render() {
    return (
      <div class="container text-center">
        <h1 class="mt-5 text-white font-weight-light">Tellban</h1>
        <p class="lead text-white-50">Your Personal Project Management Tool</p>
        <hr />
        <button type="button" class="btn btn-primary">
          Login
        </button>
        <button type="button" class="btn btn-success">
          Register
        </button>
      </div>
    );
  }
}
