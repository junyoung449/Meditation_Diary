import { createContext } from "react";

export const backgroundColors = {
  primary: "primary",
  blue: "blue",
  green: "green",
  dark: "black",
};

export const BackgroundColorContext = createContext({
  color: backgroundColors.dark,
  changeColor: (color) => {},
});
