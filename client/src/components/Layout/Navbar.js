//this is what the navbar will look like

import React, { Component } from "react";
import { withStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";

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
  render() {
    const { classes } = this.props;
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
            <Button color="primary">Login</Button>
            <Button color="primary">Register</Button>
          </Toolbar>
        </AppBar>
      </div>
    );
  }
}
export default withStyles(styles)(Navbar);
