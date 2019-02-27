import React, { Component } from "react";
import Dialog from "@material-ui/core/Dialog";
import "./landing.css";
import DialogHeader from "../Project/projectView/DialogHeader";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import PropTypes from "prop-types";
import { login } from "../../actions/SecurityActions";
import { connect } from "react-redux";
import axios from "axios";

class Landing extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isLoginFormActive: false,
      isRegistrationFormActive: false,
      email: "",
      firstName: "",
      lastName: "",
      password: "",
      confirmPassword: "",
      errors: {}
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
      isLoginFormActive: false,
      errors: {}
    });
  };

  handleCloseRegistrationForm = e => {
    this.setState({
      isRegistrationFormActive: false,
      errors: {}
    });
  };

  handleChange = event => {
    // console.log("event vaue :  " + event.target.value);
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  handleRegisterOnSubmit = event => {
    event.preventDefault();
    const newUser = {
      email: this.state.email,
      firstName: this.state.firstName,
      lastName: this.state.lastName,
      password: this.state.password,
      confirmPassword: this.state.confirmPassword
    };
    console.log("new user to be created: " + JSON.stringify(newUser));
    // this.props.createNewUser(newUser, this.props.history);
    axios
      .post("http://localhost:8080/api/users/register", newUser)
      .then(json => {
        this.setState({
          email: json.data.email,
          firstName: json.data.firstName,
          lastName: json.data.lastName,
          password: json.data.password
        });
      })
      .then(() => {
        this.handleCloseRegistrationForm(null);
      })
      .catch(json =>
        this.setState({
          errors: {
            email: json.response.data.email,
            firstName: json.response.data.firstName,
            lastName: json.response.data.lastName,
            password: json.response.data.password,
            confirmPassword: json.response.data.confirmPassword
          }
        })
      );
  };

  handleLoginSubmit = event => {
    console.log("here");
    event.preventDefault();
    const LoginRequest = {
      email: this.state.email,
      password: this.state.password
    };
    console.log("login request sent:  " + JSON.stringify(LoginRequest));
    this.props.login(LoginRequest);
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
            <form onSubmit={this.handleLoginSubmit}>
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

              <DialogActions>
                <Button onClick={this.handleCloseLoginForm} color="primary">
                  Cancel
                </Button>
                <Button type="submit" color="primary">
                  Login
                </Button>
              </DialogActions>
            </form>
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
            <form onSubmit={this.handleRegisterOnSubmit}>
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
                {this.state.errors.firstName && (
                  <span>{this.state.errors.firstName}</span>
                )}
                <TextField
                  margin="dense"
                  id="lastName"
                  name="lastName"
                  label="Last Name"
                  type="text"
                  fullWidth
                  onChange={this.handleChange}
                />
                {this.state.errors.lastName && (
                  <span>{this.state.errors.lastName}</span>
                )}
                <TextField
                  margin="dense"
                  id="regPassword"
                  name="password"
                  label="Password"
                  type="password"
                  fullWidth
                  onChange={this.handleChange}
                />
                {this.state.errors.password && (
                  <span>{this.state.errors.password}</span>
                )}
                <TextField
                  margin="dense"
                  id="confPassword"
                  name="confirmPassword"
                  label="Confirm Password"
                  type="password"
                  fullWidth
                  onChange={this.handleChange}
                />
                {this.state.errors.confirmPassword && (
                  <span>{this.state.errors.confirmPassword}</span>
                )}
                <TextField
                  margin="dense"
                  id="regEmail"
                  name="email"
                  label="Email Address"
                  type="email"
                  fullWidth
                  onChange={this.handleChange}
                />
                {this.state.errors.email && (
                  <span>{this.state.errors.email}</span>
                )}
              </DialogContent>

              <DialogActions>
                <Button
                  onClick={this.handleCloseRegistrationForm}
                  color="primary"
                >
                  Cancel
                </Button>

                <Button color="primary" type="submit">
                  Register
                </Button>
              </DialogActions>
            </form>
          </Dialog>
        )}
      </div>
    );
  }
}

Landing.propTypes = {
  login: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});
export default connect(
  mapStateToProps,
  { login }
)(Landing);
