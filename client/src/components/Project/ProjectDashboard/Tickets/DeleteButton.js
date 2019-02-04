import React, { Component, Fragment } from "react";
import IconButton from "@material-ui/core/IconButton";
import { withStyles } from "@material-ui/core/styles";
import ClearIcon from "@material-ui/icons/Clear";
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "@material-ui/core/Button";

const styles = theme => ({
  deletIcon: {
    fontSize: 15
  }
});

class DeleteButton extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isDeleteTicketDiaglogActive: false
    };
  }

  handleOpenDeleteTicketDialog = event => {
    event.stopPropagation();
    this.setState({ isDeleteTicketDiaglogActive: true });
  };

  handleCloseDeleteTicketDialog = () => {
    this.setState({ isDeleteTicketDiaglogActive: false });
  };

  handleDeleteTicket = ticket => {
    this.handleCloseDeleteTicketDialog();
    ticket.swimLane = this.props.swimLaneId;
    this.props.removeTicket(ticket);
  };
  render() {
    const { classes, ticket } = this.props;
    return (
      <Fragment>
        <IconButton
          aria-label="Delete"
          size="small"
          disableRipple
          onClick={this.handleOpenDeleteTicketDialog}
        >
          <ClearIcon size="small" className={classes.deletIcon} />
        </IconButton>
        <Dialog
          open={this.state.isDeleteTicketDiaglogActive}
          onClose={this.handleCloseDeleteTicketDialog}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description"
        >
          <DialogTitle id="alert-dialog-title">{"Remove Ticket?"}</DialogTitle>
          <DialogContent>
            <DialogContentText id="alert-dialog-description">
              Are you sure you want to delete:{" "}
              <b>{this.props.ticket.projectSequence}</b>?
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button
              onClick={this.handleCloseDeleteTicketDialog}
              color="primary"
            >
              No
            </Button>
            <Button
              onClick={this.handleDeleteTicket.bind(this, ticket)}
              color="primary"
            >
              Yes
            </Button>
          </DialogActions>
        </Dialog>
      </Fragment>
    );
  }
}
export default withStyles(styles)(DeleteButton);
