/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        int size = this.size();
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        storage[this.size()] = r;
    }

    Resume get(String uuid) {
        Resume result = null;
        int size = this.size();
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                result = storage[i];
                break;
            }
        }
        return result;
    }

    void delete(String uuid) {
        Integer index = null;
        int size = this.size();
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                index = i;
                break;
            }
        }
        if (index != null) {
            for (int i = index; i < size; i++) {
                storage[i] = storage[i + 1];
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] result = new Resume[this.size()];
        int size = this.size();
        for (int i = 0; i < size; i++) {
            result[i] = storage[i];
        }
        return result;
    }

    int size() {
        int i = 0;
        while (storage[i] != null) {
            i++;
        }
        return i;
    }
}
