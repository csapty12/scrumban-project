import React, { Component } from "react";
// import { Link } from "react-router-dom";
import axios from "axios";
import SwimLane from "./SwimLane";
import Button from "@material-ui/core/Button";
import { withStyles } from "@material-ui/core/styles";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import TextField from "@material-ui/core/TextField";
import Dialog from "@material-ui/core/Dialog";
import { DragDropContext } from "react-beautiful-dnd";

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
    const { projectTickets, swimLanes } = this.state;
    // const allTickets = projectTickets;
    const { projectIdentifier, id } = ticket;
    axios
      .delete(`http://localhost:8080/dashboard/${projectIdentifier}/${id}`, {
        data: ticket
      })
      .then(() => {
        if (projectTickets[0][ticket.projectSequence] === ticket) {
          delete projectTickets[0][ticket.projectSequence];
        }

        const allSwimLanes = [...swimLanes];
        allSwimLanes.forEach(swimLane => {
          const objectKey = Object.keys(swimLane);
          if (swimLane[objectKey]["title"] === ticket.swimLane) {
            const index = swimLane[objectKey]["ticketIds"].indexOf(
              ticket.projectSequence
            );
            swimLane[objectKey]["ticketIds"].splice(index, 1);
          }
        });

        this.setState({
          projectTickets: projectTickets,
          swimLanes: allSwimLanes
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
    axios
      .post(
        `http://localhost:8080/dashboard/${this.state.projectIdentifier}`,
        newSwimlane
      )
      .then(json => {
        this.setState({
          swimLanes: json.data.swimLanes,
          swimLaneOrder: json.data.swimLaneOrder
        });
      });
  };

  handleAddTicket = ticket => {
    axios
      .post(
        `http://localhost:8080/dashboard/${ticket.projectIdentifier}/${
          ticket.swimLane
        }`,
        ticket
      )
      .then(json => {
        let newProjectTickets = [...this.state.projectTickets];
        if (newProjectTickets.length === 0) {
          newProjectTickets.push({
            [Object.keys(json.data)[0].toString()]: json.data[
              Object.keys(json.data)[0].toString()
            ]
          });
        } else {
          newProjectTickets[0][Object.keys(json.data)[0].toString()] =
            json.data[Object.keys(json.data)[0].toString()];
        }

        let newSwimlane = [...this.state.swimLanes];
        let modifiedSwimLanes = [...this.state.swimLanes];
        const singleSwimLane = newSwimlane.filter(swimLane => {
          return Object.keys(swimLane).toString() === ticket.swimLane;
        });
        let swimLaneTicketIds = singleSwimLane[0][ticket.swimLane]["ticketIds"];
        swimLaneTicketIds.push(Object.keys(json.data)[0].toString());
        const index = modifiedSwimLanes.indexOf(singleSwimLane[0]);
        modifiedSwimLanes.splice(index, 1);
        modifiedSwimLanes.splice(index, 0, singleSwimLane[0]);

        this.setState({
          projectTickets: newProjectTickets,
          swimLanes: modifiedSwimLanes
        });
      });
  };

  onDragEnd = result => {
    const { destination, source, draggableId } = result;

    if (!destination) {
      return;
    }
    if (
      destination.droppableId === source.droppableId &&
      destination.index === source.index
    ) {
      return;
    }

    const column = this.state.swimLanes.filter(swimLane => {
      return Object.keys(swimLane)[0] === source.droppableId ? swimLane : null;
    });

    const newTaskIds = Array.from(column[0][source.droppableId].ticketIds);

    newTaskIds.splice(source.index, 1);
    newTaskIds.splice(destination.index, 0, draggableId);

    const reorderedTicketIds = {
      ...column[0][source.droppableId],
      ticketIds: newTaskIds
    };

    const modifiedSwimLane = {
      [source.droppableId]: reorderedTicketIds
    };

    console.log(
      "modified swimlane infomration: " + JSON.stringify(modifiedSwimLane)
    );
    console.log(
      "all current swimlanes: " + JSON.stringify(this.state.swimLanes)
    );

    const tempAllSwimlanes = [...this.state.swimLanes];
    const indexOfSwimLane = tempAllSwimlanes.indexOf(column[0]);
    tempAllSwimlanes.splice(indexOfSwimLane, 1);
    tempAllSwimlanes.splice(indexOfSwimLane, 0, modifiedSwimLane);
    const newState = {
      ...this.state,
      swimLanes: tempAllSwimlanes
    };

    console.log("new state of columns : " + JSON.stringify(newState));
    this.setState(newState);
  };

  render() {
    // console.log("this.allTickets: " + JSON.stringify(this.state.allTickets));
    // console.log("this.columns" + JSON.stringify(this.state.columns));
    // console.log("this.columnOrder" + JSON.stringify(this.state.columnOrder));
    // const { classes } = this.props;

    // console.log("this current state: " + JSON.stringify(this.state));
    return (
      <div className="container-fluid">
        <section className="card-horizontal-scrollable-container">
          <DragDropContext onDragEnd={this.onDragEnd}>
            {this.state.swimLaneOrder.map((swimLaneId, index) => {
              const swimLane = this.state.swimLanes[index][swimLaneId];
              const tickets = swimLane.ticketIds.map(
                ticketId => this.state.projectTickets[0][ticketId]
              );
              return (
                <SwimLane
                  key={swimLane.title}
                  swimLane={swimLane}
                  tickets={tickets}
                  projectIdentifier={this.props.projectIdentifier}
                  removeTicket={this.handleTicketDelete}
                  addTicketToSwimLane={this.handleAddTicket}
                />
              );
            })}
          </DragDropContext>
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
