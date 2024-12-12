import { createBrowserRouter } from "react-router-dom";
import Home from "./pages/homePage";
import ErrorPage from "./error-page";

export const router = createBrowserRouter([
    {
      path: "/",
      element: <Home />,
      errorElement: <ErrorPage />,
    }
  ]);