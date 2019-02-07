import React from "react";
import Dashboard from "../../../components/Project/projectView/Dashboard";
import { mount } from "enzyme";

describe("Dashboard", () => {
  it("renders", () => {
    expect(mount(<Dashboard />).length).toBe(1);
  });
});
