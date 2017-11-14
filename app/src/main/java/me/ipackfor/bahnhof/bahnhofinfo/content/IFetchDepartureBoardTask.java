package me.ipackfor.bahnhof.bahnhofinfo.content;


import java.util.List;

interface IFetchDepartureBoardTask {
    List<DepartureContent.DepartureItem> Run();
}
