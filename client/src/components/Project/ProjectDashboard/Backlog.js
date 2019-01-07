import React, { Component } from "react";
// import { Link } from "react-router-dom";
import axios from "axios";
import Column from "./Column";
import { DragDropContext } from "react-beautiful-dnd";
export default class Backlog extends Component {
  constructor(props) {
    super(props);
    this.state = {
      allTickets: [],
      columns: [],
      projectIdentifier: props.projectIdentifier,
      columnOrder: [],
      errors: {}
    };
  }

  componentDidMount() {
    axios
      .get(`http://localhost:8080/api/backlog/${this.state.projectIdentifier}`)
      .then(json => {
        this.setState({
          allTickets: json.data.tasks,
          columns: json.data.columns,
          columnOrder: json.data.columnOrder
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

  handleTicketDelete = project => {
    const backlogTickets = this.state.backlog;
    const { projectIdentifier, projectSequence } = project;
    if (
      window.confirm(
        `Are you sure you want to delete ticket: ${projectSequence}`
      )
    ) {
      axios
        .delete(
          `http://localhost:8080/api/backlog/${projectIdentifier}/${projectSequence}`
        )
        .then(() => {
          let filteredTickets = backlogTickets.filter(
            item => item.projectSequence !== projectSequence
          );
          this.setState({ backlog: filteredTickets });
        });
    }
  };

  onDragEnd = result => {
    const { destination, source, draggableId } = result;
    if (!destination) {
      return;
    }
    console.log("result: " + JSON.stringify(result));
    if (
      destination.droppableId === source.droppableId &&
      destination.index === source.index
    ) {
      return;
    }
    const columns = this.state.columns;
    var movingTicket = this.state.allTickets[0][draggableId];
    console.log(
      "moving ticket: " + JSON.stringify(this.state.allTickets[0][draggableId])
    );
    const start = columns.find(item => {
      if (item[source.droppableId] !== undefined) {
        return item;
      }
      return null;
    });

    console.log("start: " + JSON.stringify(start));
    const finish = columns.find(item => {
      if (item[destination.droppableId] !== undefined) {
        return item;
      }
      return null;
    });

    var destinationColumnTitle = finish[destination.droppableId].title
      .toUpperCase()
      .split(" ")
      .join("_");
    // destinationColumnTitle.map(item => item.toUpperCase());
    console.log("destination column title: " + destinationColumnTitle);
    var ticketAfterMove = {
      ...movingTicket,
      status: destinationColumnTitle
    };
    console.log("finish column title: " + destinationColumnTitle);
    const startTasks = start[source.droppableId];

    if (start === finish) {
      const startTaskIds = Array.from(startTasks.taskIds);
      startTaskIds.splice(source.index, 1);
      startTaskIds.splice(destination.index, 0, draggableId);

      const newColumn = {
        ...startTasks,
        taskIds: startTaskIds
      };
      const columnNewState = [...columns];
      const selectedNewColumn = columns.find((item, index) => {
        if (item[source.droppableId] !== undefined) {
          start[source.droppableId] = newColumn;

          const value = columnNewState.splice(index, 1);
          columnNewState.splice(index, 0, start);
        }
      });
      const newState = {
        ...this.state,
        columns: columnNewState
      };
      this.setState(newState);
      return;
    }
    // logic for moving between columns will go here.
    const startTaskIds = Array.from(startTasks.taskIds);
    startTaskIds.splice(source.index, 1);

    const newStart = {
      ...startTasks,
      taskIds: startTaskIds
    };
    const finishTaskIds = Array.from(finish[destination.droppableId].taskIds);
    finishTaskIds.splice(destination.index, 0, draggableId);

    const newFinish = {
      ...finish[destination.droppableId],
      taskIds: finishTaskIds
    };
    const columnNewState = [...columns];
    const selectedNewColumn = columns.find((item, index) => {
      if (item[destination.droppableId] !== undefined) {
        start[source.droppableId] = newStart;
        finish[destination.droppableId] = newFinish;
      }
    });
    const newState = {
      ...this.state,
      columns: columnNewState
    };

    this.setState(newState);
    this.persistTicketToNewColumn(ticketAfterMove);
  };

  persistTicketToNewColumn = ticketAfterMove => {
    console.log("udpated ticket: " + JSON.stringify(ticketAfterMove));
    console.log("project identifier: " + this.state.projectIdentifier);
    axios.post(
      `http://localhost:8080/api/backlog/${this.state.projectIdentifier}`,
      ticketAfterMove
    );
  };

  render() {
    // console.log("this.allTickets: " + JSON.stringify(this.state.allTickets));
    // console.log("this.columns" + JSON.stringify(this.state.columns));
    // console.log("this.columnOrder" + JSON.stringify(this.state.columnOrder));

    return (
      <div className="container-fluid">
        <section className="card-horizontal-scrollable-container">
          <DragDropContext onDragEnd={this.onDragEnd}>
            {this.state.columnOrder.map((columnId, index) => {
              const column = this.state.columns[index][columnId];
              const tasks = column.taskIds.map(
                (taskId, index) => this.state.allTickets[0][taskId]
              );
              // console.log("tasks: " + JSON.stringify(tasks));
              return <Column key={column.id} column={column} tasks={tasks} />;
            })}
          </DragDropContext>
        </section>
      </div>
    );
  }
}
