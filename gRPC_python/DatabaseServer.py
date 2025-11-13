from concurrent import futures
import grpc
import time
import threading
import Database_pb2
import Database_pb2_grpc


class DatabaseServicer(Database_pb2_grpc.DatabaseServicer):
    def __init__(self):
        self._store = {}
        self._lock = threading.Lock()

    def GetRecord(self, request, context):
        idx = request.index
        with self._lock:
            if idx in self._store:
                value = self._store[idx]
                print(f"[DEBUG] GetRecord called: index={idx}, sending value='{value}'")
                return Database_pb2.GetRecordResponse(value=self._store[idx], found=True)
            else:
                print(f"[DEBUG] GetRecord called: index={idx}, not found")
                return Database_pb2.GetRecordResponse(value="", found=False)

    def AddRecord(self, request, context):
        idx = request.index
        val = request.value
        with self._lock:
            self._store[idx] = val
            print(f"[DEBUG] AddRecord called: index={idx}, value='{val}' added")
            return Database_pb2.AddRecordResponse(ok=True)

    def GetSize(self, request, context):
        with self._lock:
            size = len(self._store)
            print(f"[DEBUG] GetSize called: size={size}")
            return Database_pb2.GetSizeResponse(size=size)


def serve(host='localhost', port=9090):
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=8))
    Database_pb2_grpc.add_DatabaseServicer_to_server(DatabaseServicer(), server)
    bind_addr = f"{host}:{port}"
    server.add_insecure_port(bind_addr)
    server.start()
    print(f"DatabaseServer gRPC server started on {bind_addr}")
    try:
        while True:
            time.sleep(60)
    except KeyboardInterrupt:
        print("Shutting down server...")
        server.stop(0)

if __name__ == '__main__':
    serve()