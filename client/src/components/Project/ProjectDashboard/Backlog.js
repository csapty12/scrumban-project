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
    if (
      destination.droppableId === source.droppableId &&
      destination.index === source.index
    ) {
      return;
    }
    const column = this.state.columns;
    const selectedColumn = column.find(item => {
      if (item[source.droppableId] !== undefined) {
        // console.log("item: " + JSON.stringify(item));
        return item;
      }
    });
    console.log("selected column: " + JSON.stringify(selectedColumn));
    const newTaskIds = Array.from(selectedColumn[source.droppableId].taskIds);
    console.log("new task id: " + newTaskIds);
    newTaskIds.splice(source.index, 1);
    newTaskIds.splice(destination.index, 0, draggableId);

    const newColumn = {
      ...selectedColumn[source.droppableId],
      taskIds: newTaskIds
    };
    console.log("NEW COLUMN: " + JSON.stringify(newColumn));
    const columnNewState = [...column];
    const selectedNewColumn = column.find((item, index) => {
      if (item[source.droppableId] !== undefined) {
        selectedColumn[source.droppableId] = newColumn;
        console.log(
          "item with index: " +
            index +
            ">>>" +
            JSON.stringify(item[source.droppableId])
        );
        const value = columnNewState.splice(index, 1);
        columnNewState.splice(index, 0, selectedColumn);
      }
    });
    console.log("columns NEW: " + JSON.stringify(selectedColumn));
    const newState = {
      ...this.state,
      columns: columnNewState
    };
    console.log("NEW STATE: " + JSON.stringify(newState));
    this.setState(newState);
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
