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
      })
      .catch(json => {
        this.setState({
          errors: {
            projectIdentifier: json.response.data.projectIdentifier
          }
        });
      });
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
    let columnData = {};
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

    this.state.columnOrder.forEach(columnId => {
      columnData[columnId] = null;
    });
    this.state.columns.forEach((column, index) => {
      columnData[this.state.columnOrder[index]] =
        column[this.state.columnOrder[index]];
    });

    const column = columnData[source.droppableId];
    const newTaskIds = Array.from(column.taskIds);

    const splicedValue = newTaskIds.splice(source.index, 1);

    newTaskIds.splice(destination.index, 0, splicedValue);

    const newColumn = {
      ...column,
      taskIds: newTaskIds.reduce(function(prev, curr) {
        return prev.concat(curr);
      })
    };
    const newState = {
      ...this.state,
      columns: {
        ...this.state.columns,
        [newColumn.id]: newColumn
      }
    };

    console.log("new state: " + JSON.stringify(newState));
  };

  render() {
    let columnData = {};
    if (this.state.columnOrder.length > 0) {
      this.state.columnOrder.forEach(columnId => {
        columnData[columnId] = null;
      });
      this.state.columns.forEach((column, index) => {
        columnData[this.state.columnOrder[index]] =
          column[this.state.columnOrder[index]];
      });
      console.log("column data: " + JSON.stringify(columnData));
      return (
        <div className="container-fluid">
          <section className="card-horizontal-scrollable-container">
            <DragDropContext onDragEnd={this.onDragEnd}>
              {this.state.columnOrder.map(columnId => {
                const column = columnData[columnId];
                const tasks = column.taskIds.map(
                  taskId => this.state.allTickets[0][taskId]
                );
                return <Column key={column.id} column={column} tasks={tasks} />;
              })}
            </DragDropContext>
          </section>
        </div>
      );
    } else {
      return null;
    }
  }
}
