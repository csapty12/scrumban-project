import React, { Component } from "react";
import Dialog from "@material-ui/core/Dialog";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import DialogContent from "@material-ui/core/DialogContent";
import TextField from "@material-ui/core/TextField";
import MenuItem from "@material-ui/core/MenuItem";
import Button from "@material-ui/core/Button";
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import DialogActions from "@material-ui/core/DialogActions";
import Typography from "@material-ui/core/Typography";
import { withStyles } from "@material-ui/core/styles";
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
class CreateTicket extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isCreateTicketDialogActive: this.props.isActive,
      summary: "",
      acceptanceCriteria: "",
      projectIdentifier: this.props.projectIdentifier,
      priority: "",
      complexity: "",
      isDropDownActive: false,
      swimlane: this.props.swimLane
    };
  }

  handleCreateTicketDialog = () => {
    this.setState({
      isCreateTicketDialogActive: !this.state.isCreateTicketDialogActive
    });
  };

  handleChange = event => {
    console.log("event val: " + event.target.value);
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  handleViewDropDownMenu = () => {
    this.setState({ isDropDownActive: !this.state.openDropDown });
  };

  handleDropDownOpen = () => {
    this.setState({ isDropDownActive: true });
  };

  handleDropDownClose = () => {
    this.setState({ isDropDownActive: false });
  };

  handleSubmit = event => {
    event.preventDefault();
    const newTicket = {
      summary: this.state.summary,
      acceptanceCriteria: this.state.acceptanceCriteria,
      projectIdentifier: this.state.projectIdentifier,
      priority: this.state.priority,
      swimLane: this.props.swimLane,
      complexity: this.state.complexity
    };
    this.props.saveNewTicket(newTicket);
  };

  render() {
    const { classes } = this.props;
    return (
      <Dialog
        open={this.state.isCreateTicketDialogActive}
        onClose={this.handleCreateTicketDialog}
      >
        <AppBar className={classes.appBar}>
          <Toolbar>
            <Typography variant="h6" color="inherit" className={classes.flex}>
              Create New Ticket
            </Typography>
          </Toolbar>
        </AppBar>
        <form onSubmit={this.handleSubmit}>
          <DialogContent>
            <TextField
              autoFocus
              margin="dense"
              id="name"
              name="summary"
              label="Summary"
              type="text"
              fullWidth
              onChange={this.handleChange}
            />
            <TextField
              id="standard-multiline-static"
              name="acceptanceCriteria"
              label="Acceptance Criteria"
              multiline
              rows="4"
              margin="normal"
              fullWidth
              onChange={this.handleChange}
            />
            <TextField
              margin="dense"
              id="complexity"
              name="complexity"
              label="Complexity (Optional)"
              type="number"
              fullWidth
              onChange={this.handleChange}
            />
            <FormControl fullWidth>
              <InputLabel htmlFor="demo-controlled-open-select">
                Priority (Optional)
              </InputLabel>
              <Select
                open={this.state.isDropDownActive}
                onClose={this.handleDropDownClose}
                onOpen={this.handleDropDownOpen}
                value={this.state.priority}
                onChange={this.handleChange}
                inputProps={{
                  name: "priority",
                  id: "demo-controlled-open-select"
                }}
              >
                <MenuItem value={"low"}>Low</MenuItem>
                <MenuItem value={"medium"}>Medium</MenuItem>
                <MenuItem value={"high"}>High</MenuItem>
              </Select>
            </FormControl>
          </DialogContent>
          <DialogActions>
            <Button onClick={this.handleCreateTicketDialog} color="primary">
              Cancel
            </Button>
            <Button
              onClick={this.handleCreateTicketDialog}
              color="primary"
              type="submit"
            >
              Create
            </Button>
          </DialogActions>
        </form>
      </Dialog>
    );
  }
}

export default withStyles(styles)(CreateTicket);
