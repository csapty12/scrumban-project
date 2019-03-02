//this is what the navbar will look like

import React, { Component, Fragment } from "react";
import { withStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import store from "../../store";
import setJwt from "../security/setJwt";
import { SET_CURRENT_USER } from "../../actions/Types";

const styles = theme => ({
  navbar: {
    background: "linear-gradient(45deg, #C7CCC8 30%, #C7CCC8 90%)",
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
  handleLogout = e => {
    localStorage.removeItem("jwtToken");
    setJwt(null);
    store.dispatch({
      type: SET_CURRENT_USER,
      payload: {}
    });
    window.location.href = "/";
  };

  render() {
    const { classes } = this.props;
    const { user } = store.getState().security;
    const userExists =
      Object.entries(user).length !== 0 && user.constructor === Object;
    // console.log("user exist: " + userExists);
    return (
      <div>
        <AppBar position="static" className={classes.navbar}>
          <Toolbar>
            <Typography variant="h6" color="primary" className={classes.grow}>
              TrellBan
            </Typography>
            <Button color="primary" href="/dashboard">
              Dashboard
            </Button>
            {userExists && (
              <Fragment>
                <i className="fas fas-user-circle mr-1" /> {user.firstName}
                <Button onClick={this.handleLogout} color="primary">
                  Logout
                </Button>
              </Fragment>
            )}
          </Toolbar>
        </AppBar>
      </div>
    );
  }
}
export default withStyles(styles)(Navbar);
