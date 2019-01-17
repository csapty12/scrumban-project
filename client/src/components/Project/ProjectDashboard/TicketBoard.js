import React, { Component, Fragment } from "react";
// import { Link } from "react-router-dom";
import axios from "axios";
import SwimLane from "./SwimLane";
import Button from "@material-ui/core/Button";
import { withStyles } from "@material-ui/core/styles";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";

const styles = theme => ({
  createButton: {
    background: "linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)",
    border: 0,
    borderRadius: 3,
    boxShadow: "0 3px 5px 2px rgba(255, 105, 135, .3)",
    color: "white",
    height: 48,
    padding: "0 30px"
  }
});

class TicketBoard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      projectTickets: [],
      swimLanes: [],
      swimLaneOrder: [],
      projectIdentifier: props.projectIdentifier,
      errors: {},
      open: false,
      name: ""
    };
  }

  componentDidMount() {
    axios
      .get(`http://localhost:8080/dashboard/${this.state.projectIdentifier}`)
      .then(json => {
        // console.log("json response: " + JSON.stringify(json));
        this.setState({
          projectTickets: json.data.tickets,
          swimLanes: json.data.swimLanes,
          swimLaneOrder: json.data.swimLaneOrder
        });
      });
    // .catch(json => {
    //   this.setState({
    //     errors: {
    //       projectIdentifier: json.response.data.projectIdentifier
    //     }
    //   });
    // });
  }

  handleTicketDelete = ticket => {
    const { projectTickets } = this.state;
    const allTickets = projectTickets;
    const { projectIdentifier, id } = ticket;

    axios
      .delete(`http://localhost:8080/dashboard/${projectIdentifier}/${id}`, {
        data: ticket
      })
      .then(json => {
        this.setState({
          projectTickets: json.data.tickets,
          swimLanes: json.data.swimLanes,
          swimLaneOrder: json.data.swimLaneOrder
        });
      });
  };

  handleChange = event => {
    // console.log("value:  " + event.target.value);
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  handleClickOpen = () => {
    this.setState({ open: true });
  };

  handleClose = () => {
    this.setState({ open: false });
  };

  handleSubmit = event => {
    event.preventDefault();
    const newSwimlane = {
      name: this.state.name
    };
    console.log("name: " + JSON.stringify(newSwimlane));
    axios
      .post(
        `http://localhost:8080/dashboard/${this.state.projectIdentifier}`,
        newSwimlane
      )
      .then(json => {
        console.log(
          "data response in ticket Board: " + JSON.stringify(json.data)
        );
        this.setState({
          swimLanes: json.data.swimLanes,
          swimLaneOrder: json.data.swimLaneOrder
        });
      });
  };
  handleAddTicket = ticket => {
    console.log("ticket : " + JSON.stringify(ticket));
    console.log("all swimlanes: " + JSON.stringify(this.state.swimLanes));
    let columnNumber = 1;

    this.state.swimLanes.forEach(swimLane => {
      console.log(swimLane["column-" + columnNumber]);
      columnNumber++;
    });
  };

  render() {
    // console.log("this.allTickets: " + JSON.stringify(this.state.allTickets));
    // console.log("this.columns" + JSON.stringify(this.state.columns));
    // console.log("this.columnOrder" + JSON.stringify(this.state.columnOrder));
    const { classes } = this.props;
    console.log("All tickets: " + JSON.stringify(this.state));
    return (
      <div className="container-fluid">
        <section className="card-horizontal-scrollable-container">
          {this.state.swimLaneOrder.map((swimLaneId, index) => {
            const swimLane = this.state.swimLanes[index][swimLaneId];

            const tickets = swimLane.ticketIds.map(
              (ticketId, index) => this.state.projectTickets[0][ticketId]
            );
            return (
              <Fragment key={swimLane.id}>
                <SwimLane
                  key={swimLane.id}
                  swimLane={swimLane}
                  tickets={tickets}
                  projectIdentifier={this.props.projectIdentifier}
                  removeTicket={this.handleTicketDelete}
                  addTicketToSwimLane={this.handleAddTicket}
                />
              </Fragment>
            );
          })}
          <div className="card--content col-10 col-lg-3">
            <Button
              variant="outlined"
              color="primary"
              onClick={this.handleClickOpen}
              disableRipple
            >
              New Swimlane &#x2b;
            </Button>
            <Dialog
              open={this.state.open}
              onClose={this.handleClose}
              aria-labelledby="form-dialog-title"
            >
              <form onSubmit={this.handleSubmit}>
                <DialogContent>
                  <TextField
                    autoFocus
                    margin="dense"
                    id="name"
                    name="name"
                    label="Swimlane Name"
                    type="text"
                    fullWidth
                    onChange={this.handleChange}
                  />
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
        </section>
      </div>
    );
  }
}

export default withStyles(styles)(TicketBoard);
