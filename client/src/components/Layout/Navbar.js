//this is what the navbar will look like

import React, { Component, Fragment } from "react";
import { withStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import { Link } from "react-router-dom";
import store from "../../store";
import { setJwt } from "../../security/SetJwt";
import { SET_CURRENT_USER } from "../../actions/Types";

const styles = theme => ({
  navbar: {
    background: "linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)",
    border: 0,
    borderRadius: 3,
    boxShadow: "0 3px 5px 2px rgba(33, 203, 243, .3)",
    color: "white",
    padding: "0 30px"
  },
  grow: {
    flexGrow: 1
  }
});
class Navbar extends Component {
  logoutUser = event => {
    localStorage.removeItem("jwt");
    localStorage.removeItem("activeProject");
    setJwt(false);
    store.dispatch({
      type: SET_CURRENT_USER,
      payload: {}
    });
    window.location.href = "/";
  };
  render() {
    const { validToken, user } = store.getState().security;
    const { classes } = this.props;
    return (
      <div>
        <AppBar position="static" className={classes.navbar}>
          <Toolbar>
            <Typography variant="h6" color="inherit" className={classes.grow}>
              TrellBan
            </Typography>
            {validToken && Object.entries(user).length !== 0 && (
              <Fragment>
                <Link to="/dashboard">
                  <Button color="inherit">{user.firstName}'s Dashboard</Button>
                </Link>
                <Link to="/">
                  <Button color="inherit" onClick={() => this.logoutUser()}>
                    Logout
                  </Button>
                </Link>
              </Fragment>
            )}
            {Object.entries(user).length === 0 && (
              <Fragment>
                <Link to="/login">
                  <Button color="inherit">Login</Button>
                </Link>
                <Link to="/register">
                  <Button color="inherit">Register</Button>
                </Link>
              </Fragment>
            )}
          </Toolbar>
        </AppBar>
      </div>
    );
  }
}
export default withStyles(styles)(Navbar);
