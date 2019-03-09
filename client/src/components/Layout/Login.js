import React, { Component } from "react";
import axios from "axios";
import { setJwt } from "../../security/SetJwt";
import jwt_decode from "jwt-decode";
import store from "../../store";
import { SET_CURRENT_USER } from "../../actions/Types";
import { Redirect } from "react-router-dom";

export default class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: "",
      errors: {},
      redirectToDashboard: false
    };
  }

  setRedirectToDashboard = () => {
    this.setState({ redirectToDashboard: true });
  };

  handleChange = event => {
    console.log("pass: " + event.target.value);
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  handleSubmit = event => {
    event.preventDefault();
    const { email, password } = this.state;
    const existingUser = {
      email: email,
      password: password
    };

    console.log("new user: " + JSON.stringify(existingUser));
    axios
      .post("/api/users/login", existingUser)
      .then(json => {
        const { token } = json.data;
        localStorage.setItem("jwt", token);
        setJwt(token);
        const decode = jwt_decode(token);
        console.log("decoded : " + JSON.stringify(decode));

        store.dispatch({
          type: SET_CURRENT_USER,
          payload: decode
        });
        this.setRedirectToDashboard();
      })

      .catch(json =>
        this.setState({
          errors: json.response.data
        })
      );
  };
  render() {
    const { errors } = this.state;
    return (
      <div className="container py-5">
        {this.state.redirectToDashboard && <Redirect to="/dashboard" />}
        <div className="row">
          <div className="col-md-12">
            <div className="row">
              <div className="col-md-6 mx-auto">
                <div className="card rounded-0">
                  <div className="card-header">
                    <h3 className="mb-0">Login</h3>
                  </div>
                  <div className="card-body">
                    <form
                      className="form"
                      autoComplete="off"
                      id="formLogin"
                      noValidate=""
                      onSubmit={this.handleSubmit}
                      method="POST"
                    >
                      <div className="form-group">
                        <label htmlFor="email">Email Address</label>
                        <input
                          type="text"
                          className="form-control form-control-lg rounded-0"
                          name="email"
                          id="email"
                          required
                          onChange={this.handleChange}
                        />
                      </div>
                      <div className="form-group">
                        <label>Password</label>
                        <input
                          type="password"
                          className="form-control form-control-lg rounded-0"
                          name="password"
                          id="password"
                          required
                          autoComplete="new-password"
                          onChange={this.handleChange}
                        />
                        {Object.keys(errors).length !== 0 && (
                          <div>Email address and password do not match</div>
                        )}
                      </div>
                      <button
                        type="submit"
                        className="btn btn-success btn-lg float-right"
                        id="btnLogin"
                      >
                        Login
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
