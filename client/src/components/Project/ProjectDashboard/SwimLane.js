import React, { Component } from "react";
import { Droppable } from "react-beautiful-dnd";
import styled from "styled-components";
import InnerList from "./InnerList";
import Button from "@material-ui/core/Button";
import { withStyles } from "@material-ui/core/styles";
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import TextField from "@material-ui/core/TextField";
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";

const TaskList = styled.div`
  flex-grow: 1;
`;
const styles = theme => ({
  ticket: {
    backgroundColor: "#BFFAFF"
  }
});

class SwimLane extends Component {
  constructor(props) {
    super(props);
    this.state = {
      open: false,
      summary: "",
      acceptanceCriteria: "",
      projectIdentifier: props.projectIdentifier,
      priority: "",
      openDropDown: false
    };
  }
  handleClickOpen = () => {
    this.setState({ open: true });
  };

  handleClose = () => {
    this.setState({ open: false });
  };

  handleDropDownOpen = () => {
    this.setState({ openDropDown: true });
  };

  handleDropDownClose = () => {
    this.setState({ openDropDown: false });
  };

  handleChange = event => {
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  handleSubmit = event => {
    event.preventDefault();
    const newTicket = {
      summary: this.state.summary,
      acceptanceCriteria: this.state.acceptanceCriteria,
      projectIdentifier: this.state.projectIdentifier,
      priority: this.state.priority,
      swimLane: this.props.swimLane.title
    };
    this.props.addTicketToSwimLane(newTicket);
  };
  render() {
    const { classes } = this.props;
    return (
      <div className="card--content col-10 col-lg-3">
        <h4 className="display-5 text-center title-backlog__border">
          {this.props.swimLane.title}
          <span style={{ fontSize: 11 }}>({this.props.tickets.length})</span>
        </h4>

        <Button
          className="card-header"
          onClick={this.handleClickOpen}
          aria-labelledby="form-dialog-title"
          disableRipple
          style={{
            backgroundColor: "#fff",
            border: "2px solid #e8e3e0",
            borderRadius: 0
          }}
        >
          Add Ticket &#x2b;
        </Button>
        <div className="card-vertical-scroll-enabled">
          <div className="text-center">
            <Dialog open={this.state.open} onClose={this.handleClose}>
              <DialogTitle id="form-dialog-title">
                Create New Ticket
              </DialogTitle>

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
                  <FormControl fullWidth>
                    <InputLabel htmlFor="demo-controlled-open-select">
                      Priority
                    </InputLabel>
                    <Select
                      open={this.state.openDropDown}
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
                  <Button onClick={this.handleClose} color="primary">
                    Cancel
                  </Button>
                  <Button
                    onClick={this.handleClose}
                    color="primary"
                    type="submit"
                  >
                    Create
                  </Button>
                </DialogActions>
              </form>
            </Dialog>
          </div>
          <Droppable droppableId={this.props.swimLane.title}>
            {(provided, snapshot) => (
              <TaskList
                // innerRef={provided.innerRef}
                ref={provided.innerRef}
                {...provided.droppableProps}
                style={{
                  background: snapshot.isDraggingOver
                    ? "linear-gradient(#2196f3, 40%,transparent)"
                    : null,
                  transition: "0.3s"
                }}
              >
                <InnerList
                  tickets={this.props.tickets}
                  removeTicket={this.props.removeTicket}
                  swimLaneId={this.props.swimLane.title}
                />
                {provided.placeholder}
              </TaskList>
            )}
          </Droppable>
        </div>
      </div>
    );
  }
}
export default withStyles(styles)(SwimLane);
