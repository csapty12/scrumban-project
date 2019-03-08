import React, { Component } from "react";
import axios from "axios";

export default class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: "",
      errors: {}
    };
  }

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
      .then(json => console.log(JSON.stringify(json)))
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
