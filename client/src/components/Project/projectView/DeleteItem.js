import React, { Component } from "react";
import Button from "@material-ui/core/Button";
import Dialog from "@material-ui/core/Dialog";
import DialogContent from "@material-ui/core/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogActions from "@material-ui/core/DialogActions";
import { withStyles } from "@material-ui/core/styles";

const styles = theme => ({});

class DeleteItem extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isDeleteDialogActive: true
    };
  }
  handleDeleteProjectActiveDialog = () => {
    this.setState({
      isDeleteDialogActive: false
    });
  };

  handleDeleteProject = project => {
    this.props.handleDeleteProject(project);
    this.handleDeleteProjectActiveDialog();
  };

  render() {
    return (
      <Dialog
        open={this.state.isDeleteDialogActive}
        onClose={this.handleDeleteProjectActiveDialog}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">{"Delete Project"}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Are you sure you want to delete:
            {" " + this.props.project.projectName}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button
            onClick={this.handleDeleteProjectActiveDialog}
            color="primary"
          >
            No
          </Button>
          <Button
            onClick={this.handleDeleteProject.bind(this, this.props.project)}
            color="primary"
            autoFocus
          >
            Yes
          </Button>
        </DialogActions>
      </Dialog>
    );
  }
}
export default withStyles(styles)(DeleteItem);
