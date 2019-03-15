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
    background: "linear-gradient(to right, #0062e6, #33aeff)",
    border: 0,
    color: "white",
    padding: "0 30px",
    boxShadow: "none"
  },
  navbarText: {
    color: "#fff",
    "&:hover": {
      color: "#fff",
      textDecoration: "none"
    }
  },
  grow: {
    flexGrow: 1,
    color: "#fff"
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
            <Typography variant="h6" className={classes.grow}>
              <Link to="/" className={classes.navbarText}>
                TrellBan
              </Link>
            </Typography>
            {validToken && Object.entries(user).length !== 0 && (
              <Fragment>
                <Link to="/dashboard" className={classes.navbarText}>
                  <Button
                    variant="text"
                    disableRipple
                    className={classes.navbarText}
                  >
                    {user.firstName}'s Dashboard
                  </Button>
                </Link>
                <Link to="/" className={classes.navbarText}>
                  <Button
                    variant="text"
                    disableRipple
                    className={classes.navbarText}
                    onClick={() => this.logoutUser()}
                  >
                    Logout
                  </Button>
                </Link>
              </Fragment>
            )}
            {Object.entries(user).length === 0 && (
              <Fragment>
                <Link to="/login" className={classes.navbarText}>
                  <Button
                    variant="text"
                    disableRipple
                    className={classes.navbarText}
                  >
                    Login
                  </Button>
                </Link>
                <Link to="/register" className={classes.navbarText}>
                  <Button
                    variant="text"
                    disableRipple
                    className={classes.navbarText}
                  >
                    Register
                  </Button>
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
