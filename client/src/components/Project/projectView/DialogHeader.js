import React from "react";
import { withStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";

const styles = theme => ({
  appBar: {
    position: "relative",
    backgroundColor: "#2196F3"
  }
});
function DialogHeader(props) {
  const { classes } = props;
  return (
    <AppBar className={classes.appBar}>
      <Toolbar>
        <Typography variant="h6" color="inherit">
          {props.type} Project
        </Typography>
      </Toolbar>
    </AppBar>
  );
}

export default withStyles(styles)(DialogHeader);
