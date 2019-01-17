package no.dervis.pact;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

public class CarController {

    private final List<Car> carList;

    public CarController() {
        this(new ArrayList<>());
    }

    public CarController(List<Car> carList) {
        this.carList = carList;

        String contentTypeJson = "application/json;charset=utf-8";

        port(9999);

        path("/api/cars", () ->  {

            /*
              Get all
             */
            get("",  (request, response) -> {
                response.type(contentTypeJson);
                return new ObjectMapper().writeValueAsString(this.carList);
            });


            /*
              Get one
             */
            get("/:id", (request, response) -> {
                if (!carExist(request.params("id"))) return handleCarDoesNotExist(response);

                response.type(contentTypeJson);
                response.status(201);

                return new ObjectMapper().writeValueAsString(getCar(request.params("id")));
            });


            /*
              Add
             */
            post("", (request, response) -> {
                addCar(new ObjectMapper().readValue(request.body(), Car.class));
                response.status(201);
                return "Ok";
            });

            // not implemented
            put("", (request, response) -> 500);
            delete("/:id", (request, response) -> 500);


            /*
              Exception handling
             */
            exception(IndexOutOfBoundsException.class, (exception, request, response) -> {
                exception.printStackTrace();
                response.status(HttpStatus.SC_BAD_REQUEST);
                response.body(exception.getLocalizedMessage());
            });

            exception(NumberFormatException.class, (exception, request, response) -> {
                exception.printStackTrace();
                response.status(HttpStatus.SC_BAD_REQUEST);
                response.body(exception.getLocalizedMessage());
            });
        });
    }

    private Object handleCarDoesNotExist(Response response) {
        response.status(404);
        return "Car does not exist.";
    }

    public void addCar(Car Car) {
        carList.add(Car);
    }

    private boolean carExist(String plateNr) {
        return !carList.isEmpty() && carList.stream().filter(car -> car.getLicenseNr().equalsIgnoreCase(plateNr)).count() == 1;
    }

    private Car getCar(String plateNr) {
        return carList.stream().filter(car -> car.getLicenseNr().equalsIgnoreCase(plateNr)).findFirst().orElseThrow(() -> new RuntimeException("No such car exists."));
    }

}
