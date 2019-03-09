import React, { Component } from "react";
import "../projectDashboard.css";
import { Draggable } from "react-beautiful-dnd";
import Card from "@material-ui/core/Card";
import CardHeader from "@material-ui/core/CardHeader";
import { withStyles } from "@material-ui/core/styles";
import classNames from "classnames";
import TicketDialog from "./TicketDialog";
import DeleteButton from "./DeleteButton";
import axios from "axios";

const styles = theme => ({
  cards: {
    marginTop: 5,
    marginBottom: 5,
    borderRadius: 1
  },
  high: {
    borderLeft: "3px solid red"
  },
  medium: {
    borderLeft: "3px solid #FF8C19"
  },
  low: {
    borderLeft: "3px solid #42CC00"
  },
  deletIcon: {
    fontSize: 15
  }
});

class Ticket extends Component {
  constructor(props) {
    super(props);
    const { ticket } = this.props;
    this.state = {
      open: false,
      isHovering: false,
      summary: ticket.summary,
      acceptanceCriteria: ticket.acceptanceCriteria,
      complexity: ticket.complexity,
      priority: ticket.priority,
      swimlaneId: this.props.swimLaneId
    };
  }
  handleChange = event => {
    this.setState({
      [event.target.name]: event.target.value
    });
  };
  handleDelete = ticket => {
    ticket.swimLane = this.props.swimLaneId;
    this.props.removeTicket(ticket);
  };

  handleClickOpen = event => {
    event.stopPropagation();
    this.setState({ open: true });
  };

  handleClose = () => {
    this.setState({ open: false });
  };

  handleEditTicket = () => {
    this.setState({
      readOnlyMode: !this.state.readOnlyMode,
      diableSaveButton: !this.state.diableSaveButton
    });
  };

  deleteButton = () => {
    const { ticket } = this.props;

    return (
      <DeleteButton
        ticket={ticket}
        removeTicket={this.handleDelete.bind(this, ticket)}
      />
    );
  };

  handleChange = event => {
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  handleUpdateTicket = ticket => {
    ticket["ticketNumberPosition"] = this.props.ticket.ticketNumberPosition;
    axios.patch(
      `/dashboard/${this.props.projectIdentifier}/${this.props.swimLaneId}/${
        ticket.id
      }`,
      ticket
    );
  };

  openProjectDetailsDialog = ticket => {
    return (
      <TicketDialog
        ticket={ticket}
        handleUpdateTicket={this.handleUpdateTicket}
      />
    );
  };

  render() {
    const { ticket, classes } = this.props;
    const priorityClass = ticket.priority;
    return (
      <Draggable
        index={this.props.index}
        draggableId={this.props.ticket.projectSequence}
        isDragDisabled={this.state.isDragDisabled}
      >
        {(provided, snapshot) => (
          <div
            {...provided.draggableProps}
            {...provided.dragHandleProps}
            ref={provided.innerRef}
            {...provided.droppableProps}
          >
            <Card
              className={classNames(
                classes.cards,
                { [classes.high]: priorityClass === "high" },
                { [classes.medium]: priorityClass === "medium" },
                { [classes.low]: priorityClass === "low" }
              )}
              style={{
                backgroundColor: snapshot.isDragging ? "#F0F2EA" : null
              }}
            >
              <CardHeader
                title={this.openProjectDetailsDialog(ticket)}
                subheader={this.props.ticket.summary}
                action={this.deleteButton()}
              />
            </Card>
          </div>
        )}
      </Draggable>
    );
  }
}
export default withStyles(styles)(Ticket);
