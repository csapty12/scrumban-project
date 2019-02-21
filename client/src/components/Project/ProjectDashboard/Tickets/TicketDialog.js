import React, { Component, Fragment } from "react";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Dialog from "@material-ui/core/Dialog";
import Typography from "@material-ui/core/Typography";
import Button from "@material-ui/core/Button";
import { withStyles } from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import Ticket from "../../../../model/Ticket";
const styles = theme => ({
  appBar: {
    position: "relative",
    backgroundColor: "#2196F3"
  },
  flex: {
    flex: 1
  },
  textField: {
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit
  }
});
class TicketDialog extends Component {
  constructor(props) {
    super(props);
    this.state = {
      diableSaveButton: true,
      readOnlyMode: true,
      openTicketDetails: false,
      isDragDisabled: false,
      isDropDownActive: false,
      ticket: new Ticket()
    };
  }
  handleEditTicket = () => {
    this.setState({
      readOnlyMode: !this.state.readOnlyMode,
      diableSaveButton: !this.state.diableSaveButton
    });
  };

  handleViewTicketDetailsDialog = () => {
    this.setState({
      openTicketDetails: !this.state.openTicketDetails,
      isDragDisabled: !this.state.isDragDisabled
    });
  };

  handleViewPriorityDropDown = () => {
    this.setState({
      isDropDownActive: !this.state.isDropDownActive
    });
  };

  handleChange = event => {
    console.log("event: " + event.target.value);
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  handleSubmit = event => {
    event.preventDefault();

    let ticket = new Ticket();
    ticket.summary = this.state.summary;
    this.setState({
      ticket: ticket
    });
    const updatedTicket = {
      id: this.props.ticket.id,
      summary: this.state.summary,
      acceptanceCriteria: this.state.acceptanceCriteria,
      complexity: this.state.complexity,
      priority: this.props.ticket.priority
    };

    console.log("updated ticket: " + JSON.stringify(updatedTicket));
  };

  render() {
    const { classes, ticket } = this.props;
    return (
      <Fragment>
        <span onClick={this.handleViewTicketDetailsDialog}>
          {ticket.projectSequence}
        </span>
        <Dialog
          open={this.state.openTicketDetails}
          onClose={this.handleViewTicketDetailsDialog}
          aria-labelledby="form-dialog-title"
          fullWidth
          maxWidth="sm"
        >
          <AppBar className={classes.appBar}>
            <Toolbar>
              <Typography variant="h6" color="inherit" className={classes.flex}>
                {ticket.projectIdentifier} / {ticket.projectSequence}
              </Typography>
              <Button color="inherit" onClick={this.handleEditTicket}>
                Edit Ticket
              </Button>
            </Toolbar>
          </AppBar>
          <form onSubmit={this.handleSubmit}>
            <DialogContent>
              <TextField
                autoFocus
                margin="dense"
                id="name"
                name="summary"
                label="Ticket Summary"
                type="text"
                fullWidth
                onChange={this.handleChange}
                defaultValue={ticket.summary}
                variant="outlined"
                InputProps={{
                  readOnly: this.state.readOnlyMode
                }}
              />
              <TextField
                margin="dense"
                id="standard-multiline-static"
                name="acceptanceCriteria"
                label="Acceptance Criteria"
                multiline
                rows="4"
                // margin="normal"
                fullWidth
                onChange={this.handleChange}
                defaultValue={ticket.acceptanceCriteria}
                variant="outlined"
                InputProps={{
                  readOnly: this.state.readOnlyMode
                }}
              />
              <TextField
                margin="dense"
                id="complexity"
                name="complexity"
                label="Ticket Complexity"
                type="number"
                fullWidth
                onChange={this.handleChange}
                defaultValue={ticket.complexity}
                variant="outlined"
                InputProps={{
                  readOnly: this.state.readOnlyMode
                }}
              />
            </DialogContent>
            <DialogActions>
              <Button
                onClick={this.handleViewTicketDetailsDialog}
                color="primary"
              >
                Close
              </Button>
              <Button
                onClick={this.handleViewTicketDetailsDialog}
                color="primary"
                disabled={this.state.diableSaveButton}
                type="submit"
              >
                Save Changes
              </Button>
            </DialogActions>
          </form>
        </Dialog>
      </Fragment>
    );
  }
}

export default withStyles(styles)(TicketDialog);