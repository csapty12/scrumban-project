import React, { Component } from "react";
import Dialog from "@material-ui/core/Dialog";
import "./landing.css";
import DialogHeader from "../Project/projectView/DialogHeader";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";

export default class Landing extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isLoginFormActive: false,
      isRegistrationFormActive: false
    };
  }

  isLoginFormDialogActive = e => {
    this.setState({
      isLoginFormActive: !this.state.isLoginFormActive
    });
  };

  isRegistrationFormDialogActive = e => {
    this.setState({
      isRegistrationFormActive: !this.state.isRegistrationFormActive
    });
  };

  handleCloseLoginForm = e => {
    this.setState({
      isLoginFormActive: false
    });
  };

  handleCloseRegistrationForm = e => {
    this.setState({
      isRegistrationFormActive: false
    });
  };

  handleChange = event => {
    console.log("event vaue :  " + event.target.value);
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  render() {
    return (
      <div className="container text-center">
        <h1 className="mt-5 text-white font-weight-light">TrellBan</h1>
        <p className="lead text-white-50">
          Your Personal Project Management Tool
        </p>
        <br />
        <hr />
        <button
          type="button"
          className="btn btn-info landing-button"
          onClick={this.isLoginFormDialogActive}
        >
          Login
        </button>
        {this.state.isLoginFormActive && (
          <Dialog
            open={this.state.isLoginFormActive}
            scroll="paper"
            fullWidth={true}
            maxWidth={"md"}
            onClose={this.handleCloseLoginForm}
          >
            <DialogHeader type="Login" />
            <form onSubmit={this.handleSubmit}>
              <DialogContent>
                <TextField
                  autoFocus
                  margin="dense"
                  id="loginEmail"
                  name="email"
                  label="Email Address"
                  type="text"
                  fullWidth
                  onChange={this.handleChange}
                />
                <TextField
                  margin="dense"
                  id="loginPassword"
                  name="password"
                  label="Password"
                  type="password"
                  fullWidth
                  onChange={this.handleChange}
                />
              </DialogContent>
            </form>
            <DialogActions>
              <Button onClick={this.handleCloseLoginForm} color="primary">
                Cancel
              </Button>
              <Button onClick={this.handleCloseLoginForm} color="primary">
                Login
              </Button>
            </DialogActions>
          </Dialog>
        )}
        <button
          type="button"
          className="btn btn-info landing-button"
          onClick={this.isRegistrationFormDialogActive}
        >
          Register
        </button>
        {this.state.isRegistrationFormActive && (
          <Dialog
            open={this.state.isRegistrationFormActive}
            scroll="paper"
            fullWidth={true}
            maxWidth={"md"}
            onClose={this.handleCloseRegistrationForm}
          >
            <DialogHeader type="Register" />
            <form onSubmit={this.handleSubmit}>
              <DialogContent>
                <TextField
                  autoFocus
                  margin="dense"
                  id="firstName"
                  name="firstName"
                  label="First Name"
                  type="text"
                  fullWidth
                  onChange={this.handleChange}
                />
                <TextField
                  margin="dense"
                  id="lastName"
                  name="lastName"
                  label="Last Name"
                  type="text"
                  fullWidth
                  onChange={this.handleChange}
                />
                <TextField
                  margin="dense"
                  id="regPassword"
                  name="password"
                  label="Password"
                  type="password"
                  fullWidth
                  onChange={this.handleChange}
                />
                <TextField
                  margin="dense"
                  id="confPassword"
                  name="confPassword"
                  label="Confirm Password"
                  type="password"
                  fullWidth
                  onChange={this.handleChange}
                />
                <TextField
                  margin="dense"
                  id="regEmail"
                  name="email"
                  label="Email Address"
                  type="email"
                  fullWidth
                  onChange={this.handleChange}
                />
              </DialogContent>
            </form>
            <DialogActions>
              <Button
                onClick={this.handleCloseRegistrationForm}
                color="primary"
              >
                Cancel
              </Button>
              <Button
                onClick={this.handleCloseRegistrationForm}
                color="primary"
              >
                Register
              </Button>
            </DialogActions>
          </Dialog>
        )}
      </div>
    );
  }
}
