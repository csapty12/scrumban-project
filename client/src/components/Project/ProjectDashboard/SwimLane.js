import React, { Component } from "react";
// import { Link } from "react-router-dom";
// import { Droppable } from "react-beautiful-dnd";
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
import axios from "axios";

const TaskList = styled.div``;
const styles = theme => ({});

class SwimLane extends Component {
  constructor(props) {
    super(props);
    // console.log("project id: " + JSON.stringify(props));
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
    console.log(event.target.value);
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
      priority: this.state.priority
    };
    console.log("new ticker: " + JSON.stringify(newTicket));
    axios.post(
      `http://localhost:8080/dashboard/${this.state.projectIdentifier}/${
        this.props.swimLane.title
      }`,
      newTicket
    );
  };
  render() {
    const { classes } = this.props;
    return (
      <div className="card--content col-10 col-lg-3">
        <h4 className="display-5 text-center title-backlog__border">
          {this.props.swimLane.title}
          <span style={{ fontSize: 11 }}>({this.props.tickets.length})</span>
        </h4>
        <div className="card-vertical-scroll-enabled">
          <a href="#">
            <div className="card text-center">
              <Button
                className="card-header"
                onClick={this.handleClickOpen}
                aria-labelledby="form-dialog-title"
                disableRipple
              >
                Add Ticket &#x2b;
              </Button>
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
          </a>
          <TaskList>
            <InnerList tickets={this.props.tickets} />
          </TaskList>
          {/*<Droppable droppableId={this.props.column.id}>
            {provided => (
              <TaskList ref={provided.innerRef} {...provided.droppableProps}>
                <InnerList tasks={this.props.tasks} />
                {provided.placeholder}
              </TaskList>
            )}
            </Droppable>*/}
        </div>
      </div>
    );
  }
}
export default withStyles(styles)(SwimLane);
