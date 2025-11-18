import grpc
import Database_pb2
import Database_pb2_grpc


def run(host='localhost', port=9090):
    channel = grpc.insecure_channel(f"{host}:{port}")
    stub = Database_pb2_grpc.DatabaseStub(channel)

    records = [
        (4101, "Appen"),
        (4102, "Ahrensburg"),
        (4103, "Wedel"),
        (4104, "Aumühle"),
        (4105, "Seevetal"),
        (4106, "Quickborn"),
    ]

    print("Adding records...")
    for idx, val in records:
        req = Database_pb2.AddRecordRequest(index=idx, value=val)
        res = stub.AddRecord(req)
        print(f" AddRecord({idx}, {val!r}) -> ok={res.ok}")

    read_idx = [4103,4107]
    for idx in read_idx:
        print(f"\nReading record {idx}...")
        r = stub.GetRecord(Database_pb2.GetRecordRequest(index=idx))
        if r.found:
            print(f" GetRecord({idx}) -> '{r.value}'")
        else:
            print(f" GetRecord({idx}) -> NOT FOUND")

    print("\nCalling GetSize()...")
    s = stub.GetSize(Database_pb2.GetSizeRequest())
    print(f" GetSize() -> {s.size}")


if __name__ == '__main__':
    run()