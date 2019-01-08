//this is what the navbar will look like

import React, { Component } from "react";
import { withStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import IconButton from "@material-ui/core/IconButton";
import MenuIcon from "@material-ui/icons/Menu";

const styles = theme => ({
  navbar: {
    backgroundColor: "#FC4A1A"
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
            <Typography variant="h6" color="inherit" className={classes.grow}>
              TrellBan
            </Typography>
            <Button color="inherit" href="/dashboard">
              Dashboard
            </Button>
            <Button color="inherit">Login</Button>
            <Button color="inherit">Register</Button>
          </Toolbar>
        </AppBar>
      </div>
    );
  }
}
export default withStyles(styles)(Navbar);
