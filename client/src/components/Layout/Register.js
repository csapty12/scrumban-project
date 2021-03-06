import React, { Component } from "react";
import axios from "axios";
import { Redirect } from "react-router-dom";
import store from "../../store";

export default class Register extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      firstName: "",
      lastName: "",
      password: "",
      confirmPassword: "",
      errors: {},
      redirectToLogin: false,
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

  handleChange = event => {
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  setRedirect = () => {
    this.setState({
      redirectToLogin: true
    });
  };

  handleSubmit = event => {
    event.preventDefault();
    const {
      email,
      firstName,
      lastName,
      password,
      confirmPassword
    } = this.state;
    const newUser = {
      email: email,
      firstName: firstName,
      lastName: lastName,
      password: password,
      confirmPassword: confirmPassword
    };

    axios
      .post("/api/users/register", newUser)
      .then(json => {
        this.setRedirect();
      })
      .catch(json => {
        this.setState({ errors: json.response.data });
      });
  };

  render() {
    const { errors } = this.state;
    return (
      <div className="container py-5">
        {this.state.redirectToLogin && <Redirect to="/login" />}
        {this.state.redirectToDashboard && <Redirect to="/dashboard" />}
        <div className="row">
          <div className="col-md-12">
            <div className="row">
              <div className="col-md-6 mx-auto">
                <div className="card rounded-0">
                  <div className="card-header">
                    <h3 className="mb-0">Register</h3>
                  </div>
                  <div className="card-body">
                    <form
                      className="form"
                      autoComplete="off"
                      id="formLogin"
                      noValidate=""
                      onSubmit={this.handleSubmit}
                    >
                      <div className="form-group">
                        <label htmlFor="firstName">First Name</label>
                        <input
                          type="text"
                          className="form-control form-control-lg rounded-0"
                          name="firstName"
                          id="firstName"
                          required
                          onChange={this.handleChange}
                          method="POST"
                        />
                      </div>
                      <div className="form-group">
                        <label htmlFor="lastName">Last Name</label>
                        <input
                          type="text"
                          className="form-control form-control-lg rounded-0"
                          name="lastName"
                          id="lastName"
                          required
                          onChange={this.handleChange}
                        />
                      </div>
                      <div className="form-group">
                        <label htmlFor="email">Email Address</label>
                        <input
                          type="email"
                          className="form-control form-control-lg rounded-0"
                          name="email"
                          id="email"
                          required
                          onChange={this.handleChange}
                        />
                        {errors.email && <div>{errors.email}</div>}
                      </div>
                      <div className="form-group">
                        <label htmlFor="password">Password</label>
                        <input
                          type="password"
                          className="form-control form-control-lg rounded-0"
                          name="password"
                          id="password"
                          required
                          onChange={this.handleChange}
                        />
                        {errors.password && <div>{errors.password}</div>}
                      </div>
                      <div className="form-group">
                        <label htmlFor="confirmPassword">
                          Confirm Password
                        </label>
                        <input
                          type="password"
                          className="form-control form-control-lg rounded-0"
                          name="confirmPassword"
                          id="confirmPassword"
                          required
                          onChange={this.handleChange}
                        />
                        {errors.confirmPassword && (
                          <div>{errors.confirmPassword}</div>
                        )}
                      </div>
                      <button
                        type="submit"
                        className="btn btn-success btn-lg float-right"
                        id="btnLogin"
                      >
                        Register
                      </button>
                    </form>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
