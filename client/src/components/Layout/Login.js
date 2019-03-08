import React, { Component } from "react";

export default class Login extends Component {
  render() {
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
                      method="POST"
                    >
                      <div className="form-group">
                        <label htmlFor="uname1">Username</label>
                        <input
                          type="text"
                          className="form-control form-control-lg rounded-0"
                          name="uname1"
                          id="uname1"
                          required=""
                        />
                        <div className="invalid-feedback">
                          Oops, you missed this one.
                        </div>
                      </div>
                      <div className="form-group">
                        <label>Password</label>
                        <input
                          type="password"
                          className="form-control form-control-lg rounded-0"
                          id="pwd1"
                          required=""
                          autoComplete="new-password"
                        />
                        <div className="invalid-feedback">
                          Enter your password too!
                        </div>
                      </div>
                      <div>
                        <label className="custom-control custom-checkbox">
                          <input
                            type="checkbox"
                            className="custom-control-input"
                          />
                          <span className="custom-control-indicator" />
                          <span className="custom-control-description small text-dark">
                            Remember me on this computer
                          </span>
                        </label>
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
