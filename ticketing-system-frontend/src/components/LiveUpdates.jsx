import React, { useEffect, useState } from "react";
import { websocket } from "../services/websocket";
import { ScrollArea } from "./ui/scroll-area";
import { Separator } from "./ui/separator";

const LiveUpdates = () => {
  const [sellingUpdates, setSellingUpdates] = useState([]);
  const [buyingUpdates, setBuyingUpdates] = useState([]);

  useEffect(() => {
    websocket.connect();

    websocket.subscribe("ticketSelling", (data) => {
      setSellingUpdates((prevUpdates) => [data, ...prevUpdates]);
    });

    websocket.subscribe("ticketBuying", (data) => {
      setBuyingUpdates((prevUpdates) => [data, ...prevUpdates]);
    });

    return () => websocket.disconnect();
  }, []);

  return (
    <div className="flex gap-2">
    <div>
    <h2 className="mb-4 text-lg font-semibold leading-none">Ticket Release Updates</h2>
    <ScrollArea className="h-[400px] w-[400px] bg-[#E9EFEC] rounded-md border p-4">
        <div className="p-4">
        {sellingUpdates.map((update, index) => (
          <>
            <div key={index} className="text-sm">
              {update}
            </div>
            <Separator className="my-2" />
          </>
        ))}
      </div>
    </ScrollArea>
    </div>
    <div>
    <h2 className="mb-4 text-lg font-semibold leading-none">Ticket Purchasing Updates</h2>
    <ScrollArea className="h-[400px] w-[400px] bg-[#E9EFEC] rounded-md border p-4">
        <div className="p-4">
        {buyingUpdates.map((update, index) => (
          <>
            <div key={index} className="text-sm">
              {update}
            </div>
            <Separator className="my-2" />
          </>
        ))}
      </div>
    </ScrollArea>
    </div>
    </div>
  );
};

export default LiveUpdates;
